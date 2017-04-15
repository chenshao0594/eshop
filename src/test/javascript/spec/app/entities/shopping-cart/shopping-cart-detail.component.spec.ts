import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ShoppingCartDetailComponent } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart-detail.component';
import { ShoppingCartService } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart.service';
import { ShoppingCart } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart.model';

describe('Component Tests', () => {

    describe('ShoppingCart Management Detail Component', () => {
        let comp: ShoppingCartDetailComponent;
        let fixture: ComponentFixture<ShoppingCartDetailComponent>;
        let service: ShoppingCartService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ShoppingCartDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ShoppingCartService,
                    EventManager
                ]
            }).overrideComponent(ShoppingCartDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShoppingCartDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShoppingCartService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ShoppingCart(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.shoppingCart).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
