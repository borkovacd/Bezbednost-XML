import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

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
}
