#!/usr/bin/env python3

import itertools
import multiprocessing
import os
import sys
import threading
from multiprocessing.pool import ThreadPool

from poeditor import POEditorAPI


def main(api_token, project_id, languages, tags, file_path):
    client = POEditorAPI(api_token)
    # See also: https://github.com/sporteasy/python-poeditor/pull/15
    client.FILTER_BY += 'proofread'
    done = 0
    lock = threading.Lock()

    def export_worker(params):
        ((language_code, language_id), (tag, module)) = params
        output = file_path.format(language_id, module)
        (_, temp) = client.export(project_id, language_code, 'android_strings', 'proofread', tag)
        try:
            if os.path.getsize(temp) > 0:
                try:
                    os.makedirs(os.path.dirname(output))
                except FileExistsError:
                    pass
                with open(output, 'w') as writer:
                    with open(temp) as reader:
                        head = reader.readline()
                        assert head.startswith('<?xml')
                        writer.write(head)
                        for line in reader:
                            writer.write(line)
        finally:
            os.remove(temp)
        with lock:
            nonlocal done
            done += 1
            print("{}/{}: {}".format(done, len(languages) * len(tags), output))

    pool = ThreadPool(max(64, multiprocessing.cpu_count()))
    pool.map(export_worker, itertools.product(languages.items(), tags.items()))


if __name__ == "__main__":
    sys.exit(main(
        api_token=sys.argv[1],
        project_id='89595',
        languages={
            # Arabic
            'ar': 'ar',
            # Chinese (simplified)
            'zh-CN': 'zh-rCN',
            # Chinese (traditional)
            'zh-TW': 'zh-rTW',
            # French
            'fr': 'fr',
            # German
            'de': 'de',
            # Japanese
            'ja': 'ja',
            # Korean
            'ko': 'ko',
            # Persian
            'fa': 'fa',
            # Russian
            'ru': 'ru',
            # Spanish
            'es': 'es',
            # Turkish
            'tr': 'tr',
            # Ukrainian
            'uk': 'uk',
        },
        tags={
            'mobile': 'core',
            'plugin': 'plugin',
        },
        file_path='{1}/src/main/res/values-{0}/strings.xml',
    ))
