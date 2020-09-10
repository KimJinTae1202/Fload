# Fload
Big Data Analyzing & Android develop & Web Site develop<br>
- email address : jintea1202@gmail.com <br>
- Demo Video : 

## Introduction
This project is a navigation application that calculates real-time road flooding risk index and guides users through safe driving paths.
1. Factors in the road hazard coefficient analysis modeling include slope, drainage treatment, altitude, and cumulative rainfall over a decade.
2. Each factor was collected using public data and APIs.
3. The collected data was processed using R and QGIS.
4. For road guidance, the intersection of each road was constructed into nodes.
5. The previous analysis of road flooding risk index was reflected in the nodes and the risk rating was divided through clustering.
6. The Django server passes the secure node to Android and Android uses T Map Api to initiate the guidance.


## Development Environment
- Android Studio
- REST API
- Django
- JavaScript
- R & QGIS

## Application Version
- minSdkVersion 29
- targetSdkVersion 29

## API
- Google Map API: https://cloud.google.com/?hl=ko
- T Map API : https://tmapapi.sktelecom.com/main.html#web/sample/TotalSample
- Android Developer : https://developer.android.com/?hl=ko

## PPT Screenshots
<img width="200" src="https://user-images.githubusercontent.com/62741210/92693464-a5212e00-f380-11ea-86a7-ef61b6233b39.PNG">
