# cvm4j

The cvm4j or CollabVM Server for Java is a "port" of the collab-vm-server to the Java platform. MDMCK10 is greatly thanked for the contribution of this project.

# Getting Started

Download the prebuilt JAR (or build it yourself) - you will now need to create cvm4j.xml, here is an example of the default one that is recommended to use.

```
<?xml version="1.0"?>
<config>
  <cvm4j>
      <width>640</width>
      <height>480</height>
      <wslisten>0.0.0.0</wslisten>
      <wsport>3401</wsport>
      <nodename>cvm4jvm</nodename>
      <vmname>cvm4j default vm name - please change this!</vmname>
  </cvm4j>
</config>
```

Then just type "java -jar path/to/jar/cvm4j.jar"

Go to a CollabVM webapp, go into the dev console and type "multicollab("localhost:3401")" - please note you might need to change the 3401 part to another port if you changed it.

# I don't see my VM, why?

It's a problem in chromium browsers, use firefox.
