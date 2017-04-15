import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TaxRateComponent } from './tax-rate.component';
import { TaxRateDetailComponent } from './tax-rate-detail.component';
import { TaxRatePopupComponent, TaxRateDialogComponent } from './tax-rate-dialog.component';
import { TaxRateDeletePopupComponent } from './tax-rate-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class TaxRateResolvePagingParams implements Resolve<any> {

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

export const taxRateRoute: Routes = [
  {
    path: 'tax-rate',
    component: TaxRateComponent,
    resolve: {
      'pagingParams': TaxRateResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxRate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'tax-rate-new',
    component: TaxRateDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxRate.home.title'
    },
  }, {
    path: 'tax-rate/:id',
    component: TaxRateDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxRate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const taxRatePopupRoute: Routes = [
  {
    path: 'tax-rate/:id/edit',
    component: TaxRatePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxRate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'tax-rate/:id/delete',
    component: TaxRateDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxRate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
