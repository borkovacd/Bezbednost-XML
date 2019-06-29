
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {RatingService} from '../service/rating.service';

@Component({
  templateUrl: './rating.component.html'
})

export class RatingComponent implements OnInit{
items = [];
  constructor(protected  router: Router,
              private ratingService: RatingService){
  }
  ngOnInit(){
    this.ratingService.getAllRatings().subscribe(data => {
      this.items = data;
    })
  }

  confirmRating(id: any){
    this.ratingService.confirmRating(id).subscribe(data => {
      this.router.navigateByUrl('/listRating');

    })
  }
}
