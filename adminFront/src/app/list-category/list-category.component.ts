import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-list-category',
  templateUrl: './list-category.component.html',
  styleUrls: ['./list-category.component.css']
})
export class ListCategoryComponent implements OnInit {

  constructor(protected router: Router) { }

  ngOnInit() {
  }

  newCategory() {
    this.router.navigateByUrl('/addCategory');
  }
}
