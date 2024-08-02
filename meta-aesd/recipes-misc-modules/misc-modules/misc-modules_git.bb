# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-jsawicki5.git;protocol=ssh;branch=master"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "7116cadc6b0f001e8d0078e7742d6ee01e0dd81c"

S = "${WORKDIR}/git"
B = "${S}/misc-modules"

inherit module update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "misc-modules-start-stop"
INITSCRIPT_PARAMS = "defaults 98"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_configure () {
    :
}

do_compile () {
    oe_runmake
}
do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${THISDIR}/files/misc-modules-start-stop ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
    install -d ${D}/lib/modules/${KERNEL_VERSION}/extra
    install -m 0644 ${B}/faulty.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/faulty.ko
    install -m 0644 ${B}/hello.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/hello.ko
}

FILES:${PN} += "${sysconfdir}/init.d/${INITSCRIPT_NAME}"
