import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductOptionValueDescriptionComponent } from './product-option-value-description.component';
import { ProductOptionValueDescriptionDetailComponent } from './product-option-value-description-detail.component';
import { ProductOptionValueDescriptionPopupComponent, ProductOptionValueDescriptionDialogComponent } from './product-option-value-description-dialog.component';
import {
    ProductOptionValueDescriptionDeletePopupComponent
} from './product-option-value-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductOptionValueDescriptionResolvePagingParams implements Resolve<any> {

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

export const productOptionValueDescriptionRoute: Routes = [
  {
    path: 'product-option-value-description',
    component: ProductOptionValueDescriptionComponent,
    resolve: {
      'pagingParams': ProductOptionValueDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionValueDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-option-value-description-new',
    component: ProductOptionValueDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionValueDescription.home.title'
    },
  }, {
    path: 'product-option-value-description/:id',
    component: ProductOptionValueDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionValueDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productOptionValueDescriptionPopupRoute: Routes = [
  {
    path: 'product-option-value-description/:id/edit',
    component: ProductOptionValueDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionValueDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'product-option-value-description/:id/delete',
    component: ProductOptionValueDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionValueDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
