
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {PriceListModel} from '../model/pricelist.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};
@Injectable()
export class PricelistService{
  private BASE_URL = 'https://localhost:8099/api/pricelist';

  constructor(private http: HttpClient) {

  }
  createPriceList(object: PriceListModel, id: any): Observable<any> {

    const body = JSON.stringify(object);
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.post(`${this.BASE_URL}/create-price-createPricelist/` + id, body, {headers: headers});
  }

  getPriceForRoom(idRoom: any): Observable<any> {
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getPriceForRoom/${idRoom}`,  {headers: headers});
  }


}
