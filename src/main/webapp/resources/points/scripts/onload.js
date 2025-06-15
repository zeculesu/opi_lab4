var drawer;
window.onload = function (){
    drawer = new Drawer();
    drawer.drawStartImage();
    var maxR = #{pointHandler.maxSelectedR}; // Получаем максимальное значение R
    drawer.redrawAll(maxR); // Вызываем функцию redrawAll с максимальным значением
    drawer.canvas.addEventListener('click', function(event) {
        drawer.parseClick(event)
    });
    setInterval(checkUpdate, 6000);
}