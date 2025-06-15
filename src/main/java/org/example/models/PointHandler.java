package org.example.models;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Observes;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.example.beans.AreaResult;
import org.example.beans.Counter;
import org.example.beans.CounterMBean;
import org.example.beans.MBeanRegistry;
import org.example.validators.PointValidator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Map;

@Named("pointHandler")
@ApplicationScoped
public class PointHandler implements Serializable {
    private CheckboxesStorage checkboxesStorage = new CheckboxesStorage();
    private Point point = new Point();
    private LinkedList<Point> points = new LinkedList<>();

    private final Counter statsMBean = new Counter();
    private final AreaResult areaResult = new AreaResult();


    public void init(@Observes @Initialized(SessionScoped.class) Object unused) {
        MBeanRegistry.registerBean(statsMBean, "counter");
        MBeanRegistry.registerBean(areaResult, "areaResult");
    }

    public void destroy(@Observes @Destroyed(SessionScoped.class) Object unused) {
        MBeanRegistry.unregisterBean(statsMBean);
        MBeanRegistry.unregisterBean(areaResult);
    }

    public void updateR(String rValue) {
        checkboxesStorage.updateCb(rValue);
        this.point.setR(checkboxesStorage.calculateMaxSelectedR()); // Устанавливаем максимальное значение R

        areaResult.calculateArea(Double.parseDouble(rValue));
    }

    public double getMaxSelectedR() {
        return checkboxesStorage.calculateMaxSelectedR();
    }

    public LinkedList<Point> getPoints() {
        return points;
    }

    @PostConstruct
    public void init() {
        initCheckboxesStorage();
    }

    public void initCheckboxesStorage() {
        checkboxesStorage.initCheckboxMap();
    }

    public void add(){
        long timer = System.nanoTime();
        point.setTime(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
        point.setStatus(PointValidator.isShoot(point.getX(), point.getY(), point.getR()));
        point.setScriptTime((long) ((System.nanoTime() - timer) * 0.01));

        this.addPoint(point);
        point = new Point(point.getX(), point.getY(), point.getR());
    }

    public void clear(){
        System.err.println("clear");
        this.points.clear();
    }

    public void addFromJS(){
        long timer = System.nanoTime();
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        params.values().forEach(System.out::println);

        try {
            float x = Float.parseFloat(params.get("x"));
            float y = Float.parseFloat(params.get("y"));
            float r = Float.parseFloat(params.get("r"));

            final Point attemptBean = new Point(x, y, r);
            attemptBean.setTime(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now().plusHours(3)));
            attemptBean.setStatus(PointValidator.isShoot(x, y, r));
            attemptBean.setScriptTime((long) ((System.nanoTime() - timer) * 0.01));
            this.addPoint(attemptBean);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void addPoint(Point point){
        this.points.addFirst(point);
        statsMBean.updateStats(point.getX(), point.getY(), point.getR(), point.getStatus());
    }

    public void setPoints(LinkedList<Point> points) {
        this.points = points;
    }

    public CheckboxesStorage getCheckboxesStorage() {
        return checkboxesStorage;
    }

    public void setCheckboxesStorage(CheckboxesStorage checkboxesStorage) {
        this.checkboxesStorage = checkboxesStorage;
    }
}