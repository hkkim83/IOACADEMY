/**
 * Created by khk on 2016. 7. 21..
 */


$(function() {
    // top 버튼이 보이는 위치
  var offset = 300,
    // 애니메이션 시간 
    scroll_top_duration = 700,
    // top 버튼
    $back_to_top = $('.top-button'),
    
    // 네비게이션이 줄어드는 위치
    nav_offset = 50,
    
    // 네비게이션 
    $navbar = $('.navbar-fixed-top'),
  
    // 텍스트 로고 
    $brand = $('.navbar-brand b');
  
    console.log($brand);
  
  $(window).scroll(function() {
    ( $(this).scrollTop() > nav_offset ) ? $navbar.addClass('top-nav-collapse') : $navbar.removeClass('top-nav-collapse');
    ( $(this).scrollTop() > nav_offset ) ? $brand.removeClass('text-large'): $brand.addClass('text-large');
    ( $(this).scrollTop() > offset ) ? $back_to_top.addClass('top-is-visible') : $back_to_top.removeClass('top-is-visible');
  });
  
  //smooth scroll to top
  $back_to_top.on('click', function(event){
    event.preventDefault();
    $('body,html').animate({
      scrollTop: 0 ,
      }, scroll_top_duration
    );
  });
  
});