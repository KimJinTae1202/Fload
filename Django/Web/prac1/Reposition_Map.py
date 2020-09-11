'''
이 파일은 views.py 파일의 position 메소드와 연계되어
안드로이드 어플에서 실시간 위치를 받아 지도에 표시하기 위한 
역할을 하는 파일입니다.
'''

import folium
import math
from haversine import haversine
#f = folium.Figure (width = 500, height = 500)

# 아래의 값은 테스트를 하기 위한 용도
location_test=[35.231010, 129.082243]
location_test2=[[35.232135, 129.084193], 
[35.232093, 129.084389], 
[35.2325113, 129.0844779], 
[35.2329885, 129.0846413], 
[35.2333445, 129.0847411], 
[35.2332966, 129.0849021], 
[35.2341953, 129.0851365], 
[35.2340646, 129.0858302], 
[35.2339299, 129.0866165], 
[35.2353483, 129.0869899], 
[35.2365889, 129.0873166], 
[35.2366264, 129.0871103], 
[35.2380016, 129.0872556], 
[35.2379833 , 129.0869376], 
[35.238968, 129.0869355], 
[35.2399632 , 129.0869373], 
[35.23994202, 129.087422], 
[35.23989548, 129.0879029], 
[35.23981384, 129.0885349], 
[35.23958117, 129.0903363], 
[35.2385329, 129.0903435], 
[35.2378263, 129.0903478], 
[35.23746, 129.090381]]
i = 0

# 현재 미구현
def get_andorid_currnet_position(let=35.231010, lon=129.082243):
    pass
    '''
    current_pos = (let, lon)
    alarm_before_pos_1 = (35.2380016, 129.0872556)
    alarm_before_pos_2 = (35.238037, 129.0878723)
    cal = round(haversine(alarm_here2, cross, unit = 'm')
    '''

# 위치값을 업데이트하기 위한 지도 그리기 메소드
def test_marking_position(position=location_test):
    global i
    print(position)
    m = folium.Map(
        location=position,
        tiles = "openstreetmap",
        zoom_start=17,
        #min_zoom=15,
        #max_zoom=15
    )#.add_to(f)

    folium.Marker(
        location=position,
        popup='let, log : {}'.format(position),
        tooltip='map1_node_{}'.format(position),
        icon=folium.Icon(color='blue',icon='')
    ).add_to(m)
    
    i = i + 1
    
    m.save('/home/FLOAD/fload_proj/prac1/templates/prac1/map_case_test.html')
