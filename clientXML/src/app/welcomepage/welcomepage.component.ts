import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ReservationService} from '../service/reservation.service';
import {SearchModel} from '../model/search.model';
import {Room} from '../model/room.model';

@Component ({
  templateUrl: './welcomepage.component.html',
  styleUrls: ['./welcomepage.component.scss']
})

export  class WelcomepageComponent {
  
  public items: Room[];

  public form: FormGroup;
  public city: AbstractControl;
  public checkInDate: AbstractControl;
  public checkOutDate: AbstractControl;
  public numberOfPersons: AbstractControl;
  constructor(protected router: Router,
              public fb: FormBuilder,
              private route: ActivatedRoute,
              private reservationService: ReservationService,) {
    this.form = this.fb.group({
      'city': ['', Validators.compose([Validators.required])],
      'checkInDate': ['', Validators.compose([Validators.required])],
      'checkOutDate': ['', Validators.compose([Validators.required])],
      'numberOfPersons': ['', Validators.compose([Validators.required])],


    })
    this.city = this.form.controls['city'];
    this.checkInDate = this.form.controls['checkInDate'];
    this.checkOutDate = this.form.controls['checkOutDate'];
    this.numberOfPersons = this.form.controls['numberOfPersons'];

  }

  login(){
    this.router.navigateByUrl('login');
  }
  registration(){
    this.router.navigateByUrl('registration');
  }
  logOut(){
    this.router.navigateByUrl('');

  }
  serachRooms(){

    if ( this.checkInDate.value > this.checkOutDate.value ) {
      alert('Molimo ponovo unesite datum dolaska i odlaska.');
      this.checkInDate.reset();
      this.checkOutDate.reset();
      return;
    }

    const object = new SearchModel(
      this.city.value,
      this.checkInDate.value,
      this.checkOutDate.value,
      this.numberOfPersons.value
    );

    this.reservationService.searchFreeRooms(object).subscribe(data => {
        this.items = data;
    });

  }
  reservationRoom(id: number) {

  }



}
