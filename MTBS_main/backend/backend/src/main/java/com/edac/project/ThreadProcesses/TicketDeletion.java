package com.edac.project.ThreadProcesses;

import com.edac.project.dao.ShowDao;
import com.edac.project.models.theater.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;


@Component
public class TicketDeletion{

    private final ShowDao showDao;
    private Date date;

    @Autowired
    public TicketDeletion(ShowDao showDao) {
        this.showDao = showDao;
        long millis = System.currentTimeMillis();
        Runnable obj = () -> {
            while (true) {
                Date currentDate = new java.sql.Date(millis);
                if(!currentDate.equals(date)){
                    System.out.println("Deleting outdated shows ...!!!");
                    List<Show> showList;
                    try {
                        showList = showDao.getOutdatedShow();
                        if(showList.size()>0){
                            showDao.deleteAll(showList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    date = currentDate;
                }
            }
        };
        Thread t = new Thread(obj);
        t.start();
    }

}
