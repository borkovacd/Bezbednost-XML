import { Component, OnInit } from '@angular/core';
import {AbstractControl, Form, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AccomodationTypeModel} from '../model/AccomodationType.model';
import {AccomodationService} from '../service/accomodationService.service';

@Component({
  selector: 'app-add-accommodationType',
  templateUrl: './add-accommodationType.component.html',
  styleUrls: ['./add-accommodationType.component.css']
})
export class AddAccommodationTypeComponent implements OnInit {

  form: FormGroup;
  name: AbstractControl;
  accommodationTypes: AccomodationTypeModel[] = []
  // newCategory: CategoryModel = new CategoryModel();
  // errorC: string ;


  constructor(protected router: Router, private fb: FormBuilder, private service: AccomodationService) {

    this.service.getAccomodationTypes().subscribe(
      data => {
        this.accommodationTypes = data;
      }
    )

    this.form = this.fb.group({
      'name': ['', Validators.compose([Validators.required])]
    });

    this.name = this.form.controls['name'];
  }

  ngOnInit() {
  }

  addNewAccommdationType() {

    const accommodationType = new AccomodationTypeModel();

    accommodationType.name = this.name.value;

    this.service.addNewAccomodationType(accommodationType).subscribe( data =>
      this.router.navigateByUrl('/home'));

    /*data => {
        this.categories = data;
        this.newCategory = new CategoryModel();
        this.errorC = '';
      },

      error => {
        this.errorC = 'Kategorija sa tim imenom vec postoji, pa se ne moze uneti!';
      }
    );*/
  }

  removeAccommodationType(accommodationType: AccomodationTypeModel) {
    this.service.removeAccomodationType(accommodationType).subscribe(
      data => {
        this.accommodationTypes = data ;
      }
    );
  }


  back() {
    this.router.navigateByUrl('/home');
  }
}
