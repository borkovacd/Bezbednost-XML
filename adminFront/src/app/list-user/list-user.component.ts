import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AdminService} from '../service/admin.service';
import {UserModel} from '../model/user.model';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})

export class ListUserComponent implements OnInit {

  public users: UserModel[];

  constructor(protected router: Router, private data: AdminService) { }

  ngOnInit() {

      this.data.getUsers().subscribe(data => {

          this.users = data;
      });

  }

  aktiviraj(email: string) {
    this.data.activate(email).subscribe(data => {
      window.location.reload();
    });
  }

  blokiraj(email: string) {
    this.data.block(email).subscribe(data => {
      window.location.reload();
    });
  }

  ukloni(email: string) {

    this.data.remove(email).subscribe(data => {
      window.location.reload();
    });
  }
}
