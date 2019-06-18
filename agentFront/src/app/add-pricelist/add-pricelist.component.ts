
import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PriceModel} from '../model/price.model';
import {PriceListModel} from '../model/pricelist.model';
import {PricelistService} from '../service/pricelist.service';

@Component({
  templateUrl: './add-pricelist.component.html'
})

export class AddPricelistComponent {
  lista = [];

  public form: FormGroup;

  public jan: AbstractControl;
  public feb: AbstractControl;
  public mar: AbstractControl;
  public apr: AbstractControl;
  public may: AbstractControl;
  public jun: AbstractControl;
  public jul: AbstractControl;
  public aug: AbstractControl;
  public sep: AbstractControl;
  public oct: AbstractControl;
  public nov: AbstractControl;
  public dec: AbstractControl;
  constructor(protected  router: Router,
              private fb: FormBuilder,
              private priceService: PricelistService,
              private route: ActivatedRoute,){
    this.form = this.fb.group({
      'jan': ['', Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
      'feb': ['', Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
      'mar': ['', Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
      'apr': ['', Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
      'may': ['', Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
      'jun': ['', Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
      'jul': ['', Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
      'aug': ['', Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
      'sep': ['', Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
      'oct': ['', Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
      'nov': ['', Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
      'dec': ['', Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
    })

    this.jan = this.form.controls['jan'];
    this.feb = this.form.controls['feb'];
    this.mar = this.form.controls['mar'];
    this.apr = this.form.controls['apr'];
    this.may = this.form.controls['may'];
    this.jun = this.form.controls['jun'];
    this.jul = this.form.controls['jul'];
    this.aug = this.form.controls['aug'];
    this.sep = this.form.controls['sep'];
    this.oct = this.form.controls['oct'];
    this.nov = this.form.controls['nov'];
    this.dec = this.form.controls['dec'];

  }
  addPriceList(){

    const  janp = new PriceModel('1', this.jan.value);
    const  febp = new PriceModel('2', this.feb.value);
    const  marp = new PriceModel('3', this.mar.value);
    const  aprp = new PriceModel('4', this.apr.value);
    const  mayp = new PriceModel('5', this.may.value);
    const  junp = new PriceModel('6', this.jun.value);
    const  julp = new PriceModel('7', this.jul.value);
    const  augp = new PriceModel('8', this.aug.value);
    const  sepp = new PriceModel('9', this.sep.value);
    const  octp = new PriceModel('10', this.oct.value);
    const  novp = new PriceModel('11', this.nov.value);
    const  decp = new PriceModel('12', this.dec.value);

    this.lista.push(janp, febp, marp, aprp, mayp, junp, julp, augp, sepp, octp, novp, decp);

    const object = new PriceListModel(this.lista);
    const idR = this.route.snapshot.params.idR;
    const idA = this.route.snapshot.params.idA;

    this.priceService.createPriceList(object, idR).subscribe(dta => {
      console.log('ok');
      this.router.navigateByUrl('welcomepage/room/' + idA + '/pricelist/' + idR);
    })
  }
  exit(){
    const idR = this.route.snapshot.params.idR;
    const idA = this.route.snapshot.params.idA;

    this.router.navigateByUrl('welcomepage/room/' + idA + '/pricelist/' + idR);

  }
}
