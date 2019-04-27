
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';

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
  public pic: AbstractControl;

  types = []
  cities = []
  categoris = []
  services = []

  constructor (protected  router: Router,
               public fb: FormBuilder,
               public route: ActivatedRoute,) {
      this.form = this.fb.group({
        'name': ['', Validators.compose([Validators.required])],
        'city': [''],
        'address': ['', Validators.compose([Validators.required])],
        'type': [''],
        'category': [''],
        'description': [''],
        'pic': ['']

      })
    this.name = this.form.controls['name'];
    this.city = this.form.controls['city'];
    this.address = this.form.controls['address'];
    this.type = this.form.controls['type'];
    this.category = this.form.controls['category'];
    this.description = this.form.controls['description'];
    this.pic = this.form.controls['pic'];

  }
  ngOnInit(){
    const mode = this.route.snapshot.params.mode;



  }
}
