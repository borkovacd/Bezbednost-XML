import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {RatingModel} from '../model/rating.model';



const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};
@Injectable()
export  class RatingService {
  private BASE_URL = 'https://localhost:8094/api/rating';

  constructor(private http: HttpClient) {

  }

  createRating(object: RatingModel, idRoom: any): Observable<any> {
    const token = localStorage.getItem('loggedUser');
    const body = JSON.stringify(object);
    const headers = new HttpHeaders({'Content-Type' : 'application/json', 'token' : token});
    return this.http.post(`${this.BASE_URL}/createRating/${token}/${idRoom}`, body, {headers: headers});
  }
  getAverageRating(idRoom: any): Observable<any> {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getAverageRating/${idRoom}`, {headers: headers});
  }

  getListOfRating(idRoom: any): Observable<any> {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getListOfRating/${idRoom}`, {headers: headers});
  }
}
