<p align="center"><a href="https://www.localzet.com" target="_blank">
  <img src="https://cdn.localzet.com/assets/media/logos/ZorinProjectsSP.svg" width="400">
</a></p>

<p align="center">
  <a href="https://android-arsenal.com/api?level=23">
    <img src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat" alt="API">
  </a>
  <a href="https://github.com/localzet/shadowsocks-android">
    <img src="https://img.shields.io/github/commit-activity/t/localzet/shadowsocks-android?label=%D0%9A%D0%BE%D0%BC%D0%BC%D0%B8%D1%82%D1%8B" alt="Коммиты">
  </a>
  <a href="https://github.com/localzet/shadowsocks-android/releases">
    <img src="https://img.shields.io/github/downloads/localzet/shadowsocks-android/total.svg" alt="Релизы">
  </a>
  <a href="https://github.com/localzet/shadowsocks-android/search?l=kotlin">
    <img src="https://img.shields.io/github/languages/top/localzet/shadowsocks-android.svg" alt="Язык">
  </a>
  <a href="https://www.gnu.org/licenses/agpl-3.0">
    <img src="https://img.shields.io/github/license/localzet/shadowsocks-android?label=%D0%9B%D0%B8%D1%86%D0%B5%D0%BD%D0%B7%D0%B8%D1%8F" alt="Лицензия">
  </a>
</p>

## Shadowsocks for Android

### PREREQUISITES

* JDK 11+
* Android SDK
  - Android NDK
* Rust with Android targets installed using `rustup target add armv7-linux-androideabi aarch64-linux-android i686-linux-android x86_64-linux-android`

### BUILD

You can check whether the latest commit builds under UNIX environment by checking Travis status.

* Install prerequisites
* Clone the repo using `git clone --recurse-submodules <repo>` or update submodules using `git submodule update --init --recursive`
* Build it using Android Studio or gradle script

## OPEN SOURCE LICENSES

<ul>
    <li>redsocks: <a href="https://github.com/darkk/redsocks/blob/master/README.md">APL 2.0</a></li>
    <li>libevent: <a href="https://github.com/libevent/libevent/blob/master/LICENSE">BSD</a></li>
    <li>tun2socks: <a href="https://github.com/ambrop72/badvpn/blob/master/COPYING">BSD</a></li>
    <li>libsodium: <a href="https://github.com/jedisct1/libsodium/blob/master/LICENSE">ISC</a></li>
    <li>OpenSSL: <a href="https://www.openssl.org/source/license-openssl-ssleay.txt">OpenSSL License</a></li>
    <li>shadowsocks-rust: <a href="https://github.com/localzet/shadowsocks-rust/blob/master/LICENSE">AGPL-3.0</a></li>
</ul>