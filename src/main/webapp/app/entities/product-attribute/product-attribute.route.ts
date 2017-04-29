import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductAttributeComponent } from './product-attribute.component';
import { ProductAttributeDetailComponent } from './product-attribute-detail.component';
import { ProductAttributePopupComponent, ProductAttributeDialogComponent } from './product-attribute-dialog.component';
import { ProductAttributeDeletePopupComponent } from './product-attribute-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductAttributeResolvePagingParams implements Resolve<any> {

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

export const productAttributeRoute: Routes = [
  {
    path: 'product-attribute',
    component: ProductAttributeComponent,
    resolve: {
      'pagingParams': ProductAttributeResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-attribute-new',
    component: ProductAttributeDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productAttribute.home.title'
    },
  }, {
    path: 'product-attribute/:id',
    component: ProductAttributeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
      path: 'product-attribute/:id/edit',
      component: ProductAttributeDialogComponent,
      data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'eshopApp.productAttribute.home.title'
      },
      canActivate: [UserRouteAccessService],
    }
];

export const productAttributePopupRoute: Routes = [
  
  {
    path: 'product-attribute/:id/delete',
    component: ProductAttributeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productAttribute.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
