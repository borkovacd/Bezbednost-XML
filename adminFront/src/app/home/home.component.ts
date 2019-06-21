import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AdminService} from '../service/admin.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(protected router: Router,private data: AdminService) { }

  ngOnInit() {
  }

  agents() {
    this.router.navigateByUrl('/listAgents');
  }

  logOut() {

    this.data.logout().subscribe(data => {

      localStorage.clear();
      this.router.navigateByUrl('');
    });
  }
  categories() {
    this.router.navigateByUrl('/addCategory');
  }

  additionalsServices() {
    this.router.navigateByUrl('/addAdditionalService');
  }

  accommodationTypes() {
    this.router.navigateByUrl('/addAccommodationTypes');

  }
}
