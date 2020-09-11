from django.urls import path
from . import views

urlpatterns = [
    path('', views.before_index, name='before_index'), # 고객 관리탭
    path('index/', views.index, name='index'), # 대시보드 탭
    path('index/parking/', views.parking, name='parking'), # 고객 차량 주차된 위치 지도
    path('maps/', views.maps, name='maps'), # 지도 및 위치 탭
    path('maps/case1_route', views.case1_route, name='maps_case1_route'), # 기존 경로를 그린 지도
    path('maps/case0_route', views.case0_route, name='maps_case0_route'), # 경로 재탐색 후 그린 지도
    #path('data/', views.data, name='data'),
    #path('data/case0_route', views.case0_route, name='case0'),
    #path('data/case1_route', views.case1_route, name='case1'),
    #path('data/case1_check', views.case1_check, name='case1_check'),
    #path('data/heatmap/', views.heatmap, name='heatmap'),
]