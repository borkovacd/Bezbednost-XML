import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'serach'
})
export class SerachPipe implements PipeTransform {

  transform(list: any, text: string): any {
    if (text == null || text.trim() == '') {
      return list;
    }

    return list.filter(n => {
        return n.accomodation.typeAccomodation.toLowerCase().includes(text.toLowerCase().trim());
      }
    );

  }
}
