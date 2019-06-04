
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccomodationModel} from "../model/accomodation.model";
import {AccomodationService} from "../service/accomodation.service";

@Component ({
  templateUrl: './add-edit-accomodation.component.html'
})

export class AddEditAccomodationComponent implements OnInit{

  public form: FormGroup;
  public name: AbstractControl;
  public city: AbstractControl;
  public address: AbstractControl;
  public type: AbstractControl;
  public category: AbstractControl;
  public description: AbstractControl;
  public capacity: AbstractControl;
  public pic: AbstractControl;

  types = []
  cities = []
  categoris = []
  services = []

  constructor (protected  router: Router,
               public fb: FormBuilder,
               public route: ActivatedRoute,
               public accService: AccomodationService,) {
      this.form = this.fb.group({
        'name': ['', Validators.compose([Validators.required])],
        'city': [''],
        'address': ['', Validators.compose([Validators.required])],
        'type': [''],
        'category': [''],
        'description': [''],
        'capacity': [''],
        'pic': ['']

      })
    this.name = this.form.controls['name'];
    this.city = this.form.controls['city'];
    this.address = this.form.controls['address'];
    this.type = this.form.controls['type'];
    this.category = this.form.controls['category'];
    this.description = this.form.controls['description'];
    this.capacity = this.form.controls['capacity'];
    this.pic = this.form.controls['pic'];

  }
  ngOnInit(){
    const mode = this.route.snapshot.params.mode;
  }

  addAccomodation(){
    const accomodation = new AccomodationModel(
      this.name.value,
      this.city.value,
      this.address.value,
      this.type.value,
      this.category.value,
      this.description.value,
      this.capacity.value,
      this.pic.value
    );

    this.accService.createAccomodation(accomodation).subscribe(data => {
      this.router.navigateByUrl('' );

    })



  }
}
