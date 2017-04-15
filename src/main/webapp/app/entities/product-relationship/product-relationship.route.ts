import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductRelationshipComponent } from './product-relationship.component';
import { ProductRelationshipDetailComponent } from './product-relationship-detail.component';
import { ProductRelationshipPopupComponent, ProductRelationshipDialogComponent } from './product-relationship-dialog.component';
import { ProductRelationshipDeletePopupComponent } from './product-relationship-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductRelationshipResolvePagingParams implements Resolve<any> {

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

export const productRelationshipRoute: Routes = [
  {
    path: 'product-relationship',
    component: ProductRelationshipComponent,
    resolve: {
      'pagingParams': ProductRelationshipResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-relationship-new',
    component: ProductRelationshipDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productRelationship.home.title'
    },
  }, {
    path: 'product-relationship/:id',
    component: ProductRelationshipDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productRelationshipPopupRoute: Routes = [
  {
    path: 'product-relationship/:id/edit',
    component: ProductRelationshipPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productRelationship.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'product-relationship/:id/delete',
    component: ProductRelationshipDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productRelationship.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
