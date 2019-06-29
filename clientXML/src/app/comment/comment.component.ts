import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RatingService} from '../service/rating.service';
import {RatingModel} from '../model/rating.model';

@Component({
  templateUrl: './comment.component.html'
})

export class CommentComponent {
  public form: FormGroup;
  public rating: AbstractControl;
  public description: AbstractControl;

  constructor(protected router: Router,
              public fb: FormBuilder,
              public route: ActivatedRoute,
              private ratingService: RatingService){
    this.form = this.fb.group({
      'rating': ['', Validators.compose([Validators.required])],
      'description': ['', Validators.compose([Validators.required])],
    })
    this.rating = this.form.controls['rating'];
    this.description = this.form.controls['description'];
  }
  confirmClick() {
    const idRoom = this.route.snapshot.params.id;
    const object = new RatingModel(
      this.description.value,
      this.rating.value
    );
    this.ratingService.createRating(object, idRoom).subscribe(data => {
      this.router.navigateByUrl('myReservations');

    })

  }
  exit(){
    this.router.navigateByUrl('myReservations');

  }


}
