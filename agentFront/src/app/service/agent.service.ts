import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {LogInModel} from '../model/logIn.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'text/plain'}),
};

@Injectable()
export class AgentService {
  private BASE_URL = 'https://localhost:8099/api/agent';

  constructor(private http: HttpClient) {

  }

  sendMessageToCentral(message: string): Observable<any> {
    return this.http.get(`${this.BASE_URL}/communicate/${message}`, {responseType: 'text'});
  }
  logIn(object: LogInModel): Observable<any> {
    const body = JSON.stringify(object);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.put(`${this.BASE_URL}/log-in `, body, {headers: headers});
  }

  getAllAgents(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getAllAgents`, httpOptions);
  }
}
