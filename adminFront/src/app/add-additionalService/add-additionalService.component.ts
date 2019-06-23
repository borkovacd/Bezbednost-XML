import { Component, OnInit } from '@angular/core';
import {AbstractControl, Form, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AdditionalServiceModel} from '../model/AdditionalService.model';
import {AccomodationService} from '../service/accomodationService.service';

@Component({
  selector: 'app-add-additionalService',
  templateUrl: './add-additionalService.component.html',
  styleUrls: ['./add-additionalService.component.css']
})
export class AddAdditionalServiceComponent implements OnInit {

  form: FormGroup;
  name: AbstractControl;
  additionalServices: AdditionalServiceModel[] = []


  constructor(protected router: Router, private fb: FormBuilder, private service: AccomodationService) {

    this.service.getAdditionalServices().subscribe(
      data => {
        this.additionalServices = data;
      }
    )

    this.form = this.fb.group({
      'name': ['', Validators.compose([Validators.required])]
    });

    this.name = this.form.controls['name'];
  }

  ngOnInit() {
  }

  addNewAdditionalService() {

    const additionalService = new AdditionalServiceModel();

    additionalService.name = this.name.value;

    this.service.addNewAdditionalService(additionalService).subscribe( data =>
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

  removeAdditionalService(additionalService: AdditionalServiceModel) {
    this.service.removeAdditionalService(additionalService).subscribe(
      data => {
        this.additionalServices = data ;
      }
    );
  }


  back() {
    this.router.navigateByUrl('/home');
  }
}
