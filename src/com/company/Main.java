package com.company;
import static org.dizitart.no2.FindOptions.*;
import static org.dizitart.no2.objects.filters.ObjectFilters.*;
import com.company.models.User;
import com.company.models.Show;
import com.company.models.Salon;
import com.company.models.Movie;
import express.Express;

import java.util.List;

import static express.database.Database.collection;

public class Main {

    public static void main(String[] args) {
        // write your code here
        var app = new Express();
        app.enableCollections();

        app.get("/rest/movie", (req, res) -> {
            var movie = collection("Movie").find();
            res.json(movie);
        });

        app.get("/rest/salon",(req, res) ->{
            var salon = collection("Salon").find();
            res.json(salon);
        });

        app.get("/rest/show",(req, res) ->{
            var show = collection("Show").find();
            res.json(show);
        });

        app.get("/rest/show/get-salon/:id",(req, res) ->{
            //get salon with showId
            var id = req.params("id");
            Show show = collection("Show").findById(id);
            Salon salon = collection("Salon").findById(show.getSalonId());

            System.out.println("found " + salon);
            //res.json(show);
            res.json(salon);
        });

        app.get("/rest/salon/get-show/:id",(req, res) ->{
            //get shows with salonId
            var id = req.params("id");
            // fetching the salon
            Salon salon = collection("Salon").findById(id);
            // fetch and filter shows with matching salonId
            List<Show> show = collection("Show").find(eq("salonId", salon.getId()));
            if(show != null) {
                res.json(show);
            }
            else{
                res.send("Shows not found");
            }
        });

        app.get("/rest/movie/get-show/:id",(req, res) ->{
            //get shows by movieId
            var id = req.params("id");
            // fetching the salon
            Movie movie = collection("Movie").findById(id);
            // fetch and filter shows with matching salonId
            List<Show> show = collection("Show").find(eq("movieId", movie.getId()));
            if(show != null) {
                res.json(show);
            }
            else{
                res.send("Shows not found");
            }
        });



        app.get("/rest/user",(req, res) ->{
            var user = collection("User").find();
            res.json(user);
        });

        app.post("rest/user", (req, res) ->{
            var user = req.body(User.class);
            var createdUser = collection("User").save(user);
            res.json(createdUser);
        });

        app.post("rest/user/:id", (req, res) ->{

        });

        app.listen(4000);
    }

}
