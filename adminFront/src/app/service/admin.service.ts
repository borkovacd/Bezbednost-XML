import { Injectable } from '@angular/core';
import {AgentModel} from '../model/Agent.model';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class AdminService {

  private BASE_URL = 'https://localhost:8443/api/admin';

  constructor(private http: HttpClient) { }

  addAgent(agent: AgentModel): Observable<any> {
    const data = JSON.stringify(agent);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post( `${this.BASE_URL}/addAgent`, data, {headers: headers});
  }


}
