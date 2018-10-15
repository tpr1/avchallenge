### This Code Simulates interactions between vehicle ECUs during a specific process (Braking).
### instructions on how to make and export a federation on WebGME are described in the following link. 
https://pages.nist.gov/ucef/development/webgme/plugins/

```
{
  "federateTypesAllowed": [
    "ABS",
    "BMS",
    "Database",
    "MCU",
    "VCU"
  ],
  "expectedFederates": [
    {
      "federateType": "ABS",
      "count": 1
    },
    {
      "federateType": "BMS",
      "count": 1
    },
    {
      "federateType": "Database",
      "count": 1
    },
    {
      "federateType": "MCU",
      "count": 1
    },
    {
      "federateType": "VCU",
      "count": 1
    }
  ],
  "lateJoinerFederates": [
    {
      "federateType": "ABS",
      "count": 0
    },
    {
      "federateType": "BMS",
      "count": 0
    },
    {
      "federateType": "Database",
      "count": 0
    },
    {
      "federateType": "MCU",
      "count": 0
    },
    {
      "federateType": "VCU",
      "count": 0
    }
  ]
}
```
### How to Run : 

In "home/vagrant/Downloads" execute this command :  git clone https://github.com/usnistgov/avchallenge.git

## REST
```
curl -X POST http://10.0.2.15:8083/fedmgr --data '{"action": "TERMINATE"}' -H "Content-Type: application/json"
curl -X POST http://10.0.2.15:8083/fedmgr --data '{"action": "START"}' -H "Content-Type: application/json"
```
## FEDMGR
```
cd /home/vagrant/Downloads/AV/AV_deployment
mvn exec:java -P FederationManagerExecJava
```
## VCU
```
cd /home/vagrant/Downloads/AV/AV_generated/AV-java-federates/AV-impl-java/VCU/target
java  -Dlog4j.configurationFile=conf/log4j2.xml -jar VCU-0.1.0-SNAPSHOT.jar  -federationId=AV -configFile=conf/VCUConfig.json
```
## MCU
```
cd /home/vagrant/Downloads/AV/AV_generated/AV-java-federates/AV-impl-java/MCU/target
java  -Dlog4j.configurationFile=conf/log4j2.xml -jar MCU-0.1.0-SNAPSHOT.jar  -federationId=AV -configFile=conf/MCUConfig.json
```
## ABS
```
cd /home/vagrant/Downloads/AV/AV_generated/AV-java-federates/AV-impl-java/ABS/target
java  -Dlog4j.configurationFile=conf/log4j2.xml -jar ABS-0.1.0-SNAPSHOT.jar  -federationId=AV -configFile=conf/ABSConfig.json
```
## BMS
```
cd /home/vagrant/Downloads/AV/AV_generated/AV-java-federates/AV-impl-java/BMS/target
java  -Dlog4j.configurationFile=conf/log4j2.xml -jar BMS-0.1.0-SNAPSHOT.jar  -federationId=AV -configFile=conf/BMSConfig.json
```

## DB

```

cd cpswt/ucef-database/target/
java -jar Database-0.0.1-SNAPSHOT.jar conf/Database.json

    "username": "root",
    "password": "c2wt",

```
