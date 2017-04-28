import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { ShoppingCart } from './shopping-cart.model';
import { ShoppingCartService } from './shopping-cart.service';

@Component({
    selector: 'jhi-shopping-cart-detail',
    templateUrl: './shopping-cart-detail.component.html'
})
export class ShoppingCartDetailComponent implements OnInit, OnDestroy {

    shoppingCart: ShoppingCart;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private shoppingCartService: ShoppingCartService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['shoppingCart']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShoppingCarts();
    }

    load(id) {
        this.shoppingCartService.find(id).subscribe((shoppingCart) => {
            this.shoppingCart = shoppingCart;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShoppingCarts() {
        this.eventSubscriber = this.eventManager.subscribe('shoppingCartListModification', (response) => this.load(this.shoppingCart.id));
    }
}
