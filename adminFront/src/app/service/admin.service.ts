import { Injectable } from '@angular/core';
import {AgentModel} from '../model/Agent.model';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginModel} from '../model/login.model';
import {UserModel} from '../model/user.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class AdminService {

  private BASE_URL = 'https://localhost:8762/authservice';

  constructor(private http: HttpClient) { }

  addAgent(agent: AgentModel): Observable<any> {
    const data = JSON.stringify(agent);
    const token = localStorage.getItem('loggedUser');
    const  headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.post( `${this.BASE_URL}/admin/addAgent`, data, {headers: headers});
  }

  login(object: LoginModel): Observable<any> {
    const body = JSON.stringify(object);
    const  headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(`${this.BASE_URL}/user/login`, body, {headers: headers});
  }

  getAgents(): Observable<AgentModel[]> {
    const token = localStorage.getItem('loggedUser');
    const  headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get<AgentModel[]>(`${this.BASE_URL}/admin/agents`, {headers: headers});
  }

  logout(): Observable<any> {
    const a = localStorage.getItem('loggedUser');
    const  headers = new HttpHeaders({'Content-Type': 'application/json', 'token': a});
    return this.http.get(`${this.BASE_URL}/user/logout` , {headers: headers});

  }


  getUsers(): Observable<UserModel[]> {
    const a = localStorage.getItem('loggedUser');
    const  headers = new HttpHeaders({'Content-Type': 'application/json', 'token': a});
    return this.http.get<UserModel[]>(`${this.BASE_URL}/admin/users` , {headers: headers});
  }

  activate(email: string) {
    const a = localStorage.getItem('loggedUser');
    const  headers = new HttpHeaders({'Content-Type': 'application/json', 'token': a});
    return this.http.get(`${this.BASE_URL}/admin/activateUser/${email}`, {headers: headers});
  }

  remove(email: string) {
    const a = localStorage.getItem('loggedUser');
    const  headers = new HttpHeaders({'Content-Type': 'application/json', 'token': a});
    return this.http.get(`${this.BASE_URL}/admin/removeUser/${email}`, {headers: headers});
  }

  block(email: string){
    const a = localStorage.getItem('loggedUser');
    const  headers = new HttpHeaders({'Content-Type': 'application/json', 'token': a});
    return this.http.get(`${this.BASE_URL}/admin/blockUser/${email}`, {headers: headers});
  }

}
