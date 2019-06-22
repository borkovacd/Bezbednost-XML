import { Component, OnInit } from '@angular/core';
import {AbstractControl, Form, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {CategoryModel} from '../model/Category.model';
import {AccomodationService} from '../service/accomodationService.service';
import {AgentModel} from '../model/Agent.model';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {

  form: FormGroup;
  name: AbstractControl;
  categories: CategoryModel[] = []
  // newCategory: CategoryModel = new CategoryModel();
  // errorC: string ;


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

    const category = new CategoryModel();

    category.name = this.name.value;

    this.service.addNewCategory(category).subscribe( data =>
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

  removeCategory(cat: CategoryModel) {
    this.service.removeCategory(cat).subscribe(
      data => {
        this.categories = data ;
      }
    );
  }



}
