1.把location\devops\docker\images\mysql\sql\init.sql文件导入mysql

2.修改location\src\main\resources\application.yml中的spring.datasource.url中的用户名和密码

3.在location根目录运行gradle build

4.进入D:\JavaSourceCode\location\build\libs文件夹，执行java -Djava.security.egd=file:/dev/./urandom -jar location-1.0.1.jar

5.访问以下URL
http://localhost:5678/location/v1/nearby?pageNumber=1&pageSize=10&radius=500000&lng=104.07&lat=30.67
根据经纬度查询
radius：半径，以米为单位
lng：经度
lat：纬度
http://localhost:5678/location/v1/nearby/<location_id>?pageNumber=1&pageSize=10&radius=5000000
根据ID查询
location_id：location的ID，也可以看成是设备的ID
radius：半径，以米为单位