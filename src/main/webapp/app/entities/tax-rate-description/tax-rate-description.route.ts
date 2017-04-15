import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TaxRateDescriptionComponent } from './tax-rate-description.component';
import { TaxRateDescriptionDetailComponent } from './tax-rate-description-detail.component';
import { TaxRateDescriptionPopupComponent, TaxRateDescriptionDialogComponent } from './tax-rate-description-dialog.component';
import { TaxRateDescriptionDeletePopupComponent } from './tax-rate-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class TaxRateDescriptionResolvePagingParams implements Resolve<any> {

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

export const taxRateDescriptionRoute: Routes = [
  {
    path: 'tax-rate-description',
    component: TaxRateDescriptionComponent,
    resolve: {
      'pagingParams': TaxRateDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxRateDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'tax-rate-description-new',
    component: TaxRateDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxRateDescription.home.title'
    },
  }, {
    path: 'tax-rate-description/:id',
    component: TaxRateDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxRateDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const taxRateDescriptionPopupRoute: Routes = [
  {
    path: 'tax-rate-description/:id/edit',
    component: TaxRateDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxRateDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'tax-rate-description/:id/delete',
    component: TaxRateDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxRateDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
