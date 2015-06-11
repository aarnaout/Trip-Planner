# Trip-Planner
A java based app to get public transportation directions in the City of Chicago using CTA trains and buses. The app takes in consideratino a reasonable walking distance from/to origin/distnation and when transfer.

All Chicago CTA trains except Yellow Line are included, in addition to twenty bus lines:
 1,2,3,4,6,7,9,10,11,12,21,29,53,55,72,73,74,91,92,152. However, the app can support as many new bus lines or trains as needed, just need to download the appropriate information from CTA website.
 
The app will prompt the user to enter the coordinates of both the origin and destination either manually or from a menu, and then it decides which one of the following modes is the most efficient:
 - Trains only mode
 - Buses only mode
 - Hybrid mode: Contains three sub modes: (trains --> buses) , (buses --> trains) or (buses --> trains --> buses).

The app will print out the trip information along with the whole distance that the user has to walk. This information
will be printed to the console and stored into a txt file with a timestamped title.
