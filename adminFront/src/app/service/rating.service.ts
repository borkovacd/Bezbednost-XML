import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class RatingService {

  private BASE_URL = 'https://localhost:8762/api/rating';

  constructor(private http: HttpClient) {
  }


  getAllRatings(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getAllRatings`, httpOptions);
  }

  confirmRating(idRating: any): Observable<any> {
    return this.http.get(`${this.BASE_URL}/confirmRating/${idRating}`, httpOptions);
  }

}
