import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AdminService} from '../service/admin.service';
import {AgentModel} from '../model/Agent.model';

@Component({
  selector: 'app-list-agent',
  templateUrl: './list-agent.component.html',
  styleUrls: ['./list-agent.component.css']
})
export class ListAgentComponent implements OnInit {

  public agents: AgentModel[];

  constructor(protected router: Router, private data: AdminService) { }

  ngOnInit() {

    this.data.getAgents().subscribe( data => this.agents = data);

  }

  newAgent() {
    this.router.navigateByUrl('/addAgent');
  }

  back() {
    this.router.navigateByUrl('/home');
  }
}
