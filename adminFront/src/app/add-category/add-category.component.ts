import { Component, OnInit } from '@angular/core';
import {AbstractControl, Form, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {CategoryModel} from '../model/Category.model';
import {AccomodationService} from '../service/accomodationService.service';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {

  form: FormGroup;
  name: AbstractControl;
  categories: CategoryModel[] = []
  newCategory: CategoryModel = new CategoryModel();
  errorC: string ;


  constructor(protected router: Router, private fb: FormBuilder, private service: AccomodationService) {

    this.service.getCategories().subscribe(
      data => {
        this.categories = data;
      }
    )

    this.form = this.fb.group({
      'name': ['', Validators.compose([Validators.required])]
    });

    this.name = this.form.controls['name'];
  }

  ngOnInit() {
  }

  addNewCategory() {

    this.service.addNewCategory(this.newCategory).subscribe(
      data => {
        this.categories = data;
        this.newCategory = new CategoryModel();
        this.errorC = '';
      },

      error => {
        this.errorC = 'Kategorija sa tim imenom vec postoji, pa se ne moze uneti!';
      }
    );
  }

  removeCategory(cat: CategoryModel) {
    this.service.removeCategory(cat).subscribe(
      data => {
        this.categories = data ;
      }
    );
  }



}
