import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CurrencyComponent } from './currency.component';
import { CurrencyDetailComponent } from './currency-detail.component';
import { CurrencyPopupComponent, CurrencyDialogComponent } from './currency-dialog.component';
import { CurrencyDeletePopupComponent } from './currency-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CurrencyResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const currencyRoute: Routes = [
  {
    path: 'currency',
    component: CurrencyComponent,
    resolve: {
      'pagingParams': CurrencyResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'currency/:id',
    component: CurrencyDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'currency-new',
    component: CurrencyDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'currency/:id/edit',
    component: CurrencyDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const currencyPopupRoute: Routes = [
  
  
  {
    path: 'currency/:id/delete',
    component: CurrencyDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
