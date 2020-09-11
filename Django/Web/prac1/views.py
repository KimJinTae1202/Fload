from django.shortcuts import render
from django.http import HttpResponse, HttpResponseRedirect
from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator
from django.urls import reverse
from .models import *
from .Reposition_Map import *
from django.template import loader

# 메인 페이지 전에 관리하고자 하는 고객을 선택하는 페이지(before_index.html)를 불러오는 메소드
def before_index(request):
    return render(request, 'prac1/before_index.html')

# 대시보드 탭(index.html)을 불러오는 메소드
def index(request):
    return render(request, 'prac1/index.html')

# 대시보드 탭에서 고객이 주차한 차량 위치 나타내는 지도(parking_where.html) 불러오기
def parking(request):
    return render(request, 'prac1/parking_where.html')

# 지도 및 위치 탭(map.html) 불러오기
def maps(request):
    return render(request, 'prac1/map.html')

# 지도 및 위치 탭에서 초기 경로 탐색한 지도(case0.html) 불러오기
def case0_route(request):
    return render(request, 'prac1/case0.html')

# 지도 및 위치 탭에서 경로 재탐색한 지도(case1.html) 불러오기
def case1_route(request):
    return render(request, 'prac1/case1.html')  

# 안드로이드 어플에서 위치를 보내주면 실시간으로 받는 position 메소드
@method_decorator(csrf_exempt,name='dispatch')
def position(request):
    my_lat = float(request.GET.get('my_lat'))
    my_long=float(request.GET.get('my_long'))
    my_loc=[my_lat, my_long]
    get_andorid_currnet_position(my_lat, my_long)

    danger_node=(35.237373, 129.087192)
    distance=haversine(danger_node,my_loc,unit='m')

    print("====요청 들어옴====")
    print("현재위치: ",my_lat,my_long)
    print(int(distance))
    
    response_str="safe"

    distance=int(distance)
    if(distance<10):
        response_str="replay"
    elif(distance<5):
        response_str="danger"
    
    return HttpResponse('replay')