import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RatingService} from '../service/rating.service';

@Component ({
  templateUrl: './roomRating.component.html',
})

export class RoomRatingComponent implements OnInit{
  items = [];
  rating: any;
  constructor(protected  router: Router,
              private ratingService: RatingService,
              public route: ActivatedRoute){}

  ngOnInit(){
    const idRoom = this.route.snapshot.params.idRoom;

    this.ratingService.getAverageRating(idRoom).subscribe(data => {
      this.rating = data;
    })

    this.ratingService.getListOfRating(idRoom).subscribe(data => {
      this.items = data;
    })


  }
}
