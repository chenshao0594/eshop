import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductImageDetailComponent } from '../../../../../../main/webapp/app/entities/product-image/product-image-detail.component';
import { ProductImageService } from '../../../../../../main/webapp/app/entities/product-image/product-image.service';
import { ProductImage } from '../../../../../../main/webapp/app/entities/product-image/product-image.model';

describe('Component Tests', () => {

    describe('ProductImage Management Detail Component', () => {
        let comp: ProductImageDetailComponent;
        let fixture: ComponentFixture<ProductImageDetailComponent>;
        let service: ProductImageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductImageDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductImageService,
                    EventManager
                ]
            }).overrideComponent(ProductImageDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductImageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductImageService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductImage(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productImage).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
