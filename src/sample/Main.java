package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLAYER_HEIGHT = 100;
    private static final int PLAYER_WIDTH = 15;
    private static final double BALL_R = 15;
    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private int scorePlayerOne = 0;
    private int scorePlayerTwo = 0;
    private double playerOneYPos = HEIGHT/2;
    private double playerTwoYPos = HEIGHT/2;
    private double ballXPos = WIDTH/2;
    private double ballYPos = WIDTH/2;
    private boolean gameStarted;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e -> run(graphicsContext)));
        t1.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseMoved(e -> playerOneYPos = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);

        primaryStage.setTitle("PONG Game by Karol T.");
        primaryStage.setScene(new Scene(new StackPane(canvas)));
        primaryStage.show();
        t1.play();
    }

    private void run (GraphicsContext graphicsContext){
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0,0, WIDTH, HEIGHT);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(Font.font(25));
        graphicsContext.fillText(String.valueOf(scorePlayerOne), 200, 300);
        graphicsContext.fillText(String.valueOf(scorePlayerTwo), 600, 300);

        graphicsContext.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
        if (gameStarted){
            ballXPos+=ballXSpeed;
            ballYPos+=ballYSpeed;
            score();

            if(ballYPos >= HEIGHT-BALL_R){
                ballYSpeed *= -1;
            }
            if(ballYPos <= 0){
                ballYSpeed *= -1;
            }
            if (ballXPos <= PLAYER_WIDTH && (ballYPos>playerOneYPos && ballYPos<playerOneYPos+PLAYER_HEIGHT)){
                ballXSpeed *= -1;
                score();
            }
            if (ballXPos >= WIDTH - PLAYER_WIDTH*2 ){
                ballXSpeed *= -1;
                score();
            }
        }
        System.out.println("PLAYER TWO Y POS: " + playerTwoYPos + " || BALL Y POS: " +ballYPos);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, playerOneYPos, PLAYER_WIDTH,PLAYER_HEIGHT);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(WIDTH-PLAYER_WIDTH,playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        playerTwoYPos=ballYPos-50;
    }

    public void setGameStarted() {
        if (ballXSpeed<0){
            ballXSpeed-=1;
        }
        else ballXSpeed+=1;

        if (ballYSpeed<0){
            ballYSpeed-=1;
        }
        else ballYSpeed+=1;
        ballYPos = WIDTH/2;
        ballXPos = WIDTH/2;
        this.gameStarted = false;
    }

    private void score(){
        if(ballXPos >= WIDTH-BALL_R){
            scorePlayerOne+=1;
            setGameStarted();

        }
        if(ballXPos <= 0){
            scorePlayerTwo+=1;
            setGameStarted();
        }

    }
}

