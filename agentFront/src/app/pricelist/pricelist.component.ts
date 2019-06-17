
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PricelistService} from '../service/pricelist.service';

@Component({
  templateUrl: './pricelist.component.html'
})

export class PricelistComponent implements OnInit{
  items=[];
  constructor(protected  router: Router,
              private route: ActivatedRoute,
              private priceService: PricelistService,){

  }

  ngOnInit(){
    const idR = this.route.snapshot.params.idR;
    this.priceService.getPriceForRoom(idR).subscribe(data => {
      this.items = data;
    })

  }
  addPriceList(){
    const idR = this.route.snapshot.params.idR;
    const idA = this.route.snapshot.params.idA;

    this.router.navigateByUrl('welcomepage/room/'+ idA + '/pricelist/add/' + idR );
  }
  goBack(){
    const idA = this.route.snapshot.params.idA;
    this.router.navigateByUrl('welcomepage/room/'+ idA );
  }

}
