# WeeklyCalendar
Home assignment from PARiM solutions estonia. This project shows weekly calendar and estonian holidays in each week.

Implementation is done using MVVM architecture with an additional layer of Module.

This project also includes bitbucket-pipeline.yml and other gradle configurations to make it work with bitbucket. Just need to add organization key and other attributes

Many unit cases are written using roboelectric, mockito and power mockito. One can take example from them and write more cases to increase overall coverage

Realm is used as database which stores estonians holidays

Channels are used error handling with flows

UI is simple and a library is used for horizontal UI depiction of calendar. It can be written to get more control

Retrofit and okHttp is used for network calling

API to fetch data is dynamic in nature so it would need a little manual parsing

There is a bug which is in the selection of drop down. That can be resolved

DI is not implemented yet but it can be done as future enhancement
