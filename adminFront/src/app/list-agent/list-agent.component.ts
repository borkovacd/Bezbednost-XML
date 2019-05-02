import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-list-agent',
  templateUrl: './list-agent.component.html',
  styleUrls: ['./list-agent.component.css']
})
export class ListAgentComponent implements OnInit {

  constructor(protected router: Router) { }

  ngOnInit() {
  }

  newAgent() {
    this.router.navigateByUrl('/addAgent');
  }
}
