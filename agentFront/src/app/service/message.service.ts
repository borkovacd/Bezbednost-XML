import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {AnswerModel} from '../model/answer.model';



const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};
@Injectable()
export  class MessageService {
  private BASE_URL = 'https://localhost:8099/api/message';

  constructor(private http: HttpClient) {

  }
  getAllMessages(): Observable<any> {
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getAllMessages/${token}`, {headers: headers});
  }




  answerToClient(object: AnswerModel): Observable<any> {
    const token = localStorage.getItem('agentId');
    const body = JSON.stringify(object);
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.post(`${this.BASE_URL}/createAnswer/${token}`, body,{headers: headers})
  }

}
