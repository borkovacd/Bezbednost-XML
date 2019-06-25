import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';



const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};
@Injectable()
export  class MessageService {
  private BASE_URL = 'https://localhost:8443/api/message';

  constructor(private http: HttpClient) {

  }

  getAllMessages(): Observable<any> {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getAllMessages/${token}`, {headers: headers});
  }

  getAllResponses(): Observable<any> {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getAllResponsesFromAgent/${token}`, {headers: headers});
  }


}
