import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {MessageModel} from '../model/message.model';



const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};
@Injectable()
export  class MessageService {
  private BASE_URL = 'https://localhost:8762/resservice/api/message';

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


  checkIfHasReservation(): Observable<any> {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type' : 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/checkIfHasReservation/${token}`, {headers: headers});
  }


  getAppropriateAgents(): Observable<any> {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getAppropriateAgents/${token}`, {headers: headers});
  }

  sendToAgent(message: MessageModel): Observable<any> {
    const token = localStorage.getItem('loggedUser');
    const body = JSON.stringify(message);
    const headers = new HttpHeaders({'Content-Type' : 'application/json', 'token' : token});
    return this.http.post(`${this.BASE_URL}/createMessage/${token}`, body, {headers: headers});
  }
}
