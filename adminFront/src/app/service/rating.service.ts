import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class RatingService {

  private BASE_URL = 'https://localhost:8094/api/rating';


  constructor(private http: HttpClient) {
  }


  getAllRatings(): Observable<any> {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getAllRatings`, {headers : headers});
  }

  confirmRating(idRating: any): Observable<any> {
    return this.http.post(`${this.BASE_URL}/confirmRating/${idRating}`, httpOptions);
  }

}
